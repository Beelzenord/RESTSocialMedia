var express = require('express'),
    path = require('path'),
    bodyParser = require('body-parser'),
    app = express(),
    expressValidator = require('express-validator');

app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true })); //support x-www-form-urlencoded
app.use(bodyParser.json());
app.use(expressValidator());

var dbconfig = {
    user: process.env.SQL_USER,
    password: process.env.SQL_PASSWORD,
    database: process.env.SQL_DATABASE,
    socketPath: `/cloudsql/${process.env.INSTANCE_CONNECTION_NAME}`,
    typeCast: function castField(field, useDefaultTypeCasting) {
        // We only want to cast bit fields that have a single-bit in them. If the field
        // has more than one bit, then we cannot assume it is supposed to be a Boolean.
        if ((field.type === "BIT") && (field.length === 1)) {
            var bytes = field.buffer();
            // A Buffer in Node represents a collection of 8-bit unsigned integers.
            // Therefore, our single "bit field" comes back as the bits '0000 0001',
            // which is equivalent to the number 1.
            return (bytes[0] === 1);
        }

        return (useDefaultTypeCasting());
    }
};

var jsORM = require('js-hibernate');
var session = jsORM.session(dbconfig);

var userMap = session.tableMap('t_users')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id', { isAutoIncrement: true })
.columnMap('occupation', 'occupation')
.columnMap('pass', 'pass')
.columnMap('username', 'username');

var entityMap = session.tableMap('t_messages')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id', { isAutoIncrement: true })
.columnMap('isRead', 'isRead')
.columnMap('isDeleted', 'isDeleted')
.columnMap('messageText', 'messageText')
.columnMap('timeSent', 'timeSent')
.columnMap('Receiver_id', 'Receiver_id')
.columnMap('Sender_id', 'Sender_id');  

/*** Create a Json document for with all entities ***/
function toJsonTree(result) {
    var tMessagess = [];
    for (i = 0; i < result.length; i++) {
        jsonBranch(tMessagess, result[i], i);
    }
    return tMessagess;
}

/*** Create a json branch for this entity including TUsers ***/
function jsonBranch(tMessagess, row, num) {
    tMessagess.push(
        tMessages = {
            id: row.id,
            isRead: row.isRead,
            isDeleted: row.isDeleted,
            messageText: row.messageText,
            timeSent: row.timeSent,
            receiverid: {
                id: row.rid,
                occupation: row.roccupation,
                pass: row.rpass,
                username: row.rusername
            },
            senderid: {
                id: row.sid,
                occupation: row.soccupation,
                pass: row.spass,
                username: row.susername
            }
        }
    );
}

app.get('/', function(req, res) {
    res.send('Welcome to messages');
});

//RESTful route
var router = express.Router();

router.use(function(req, res, next) {
    console.log(req.method, req.url);
    next();
});

var curut = router.route('/:id');

curut.get(function(req, res, next) {
    var id = req.params.id;

    var str = 'SELECT m.id, m.isRead, m.isDeleted, m.messageText, m.timeSent, ' + 
    'r.id AS rid, r.occupation AS roccupation, r.pass AS rpass, r.username AS rusername, ' + 
    's.id AS sid, s.occupation AS soccupation, s.pass AS spass, s.username AS susername ' +
    'FROM t_messages m ' +
    'INNER JOIN t_users r ON r.id = m.Receiver_id ' +
    'INNER JOIN t_users s ON s.id = m.Sender_id WHERE m.id = ' + id;
    var query = session.executeSql(str);

    query.then(function(result) {
        var jsonresult = toJsonTree(result);
        res.json(jsonresult[0]);
    }).catch(function(error) {
        console.log('Error: ' + error);
        res.json();
    });
});

curut.delete(function(req, res) {
    var id = req.params.id;
    var str = "DELETE FROM socialmedia.t_messages WHERE id = " + id;
    var query = session.executeSql(str);
    query.then(function(result) {
        console.log("deleted: " + id);
        res.json();
    }).catch(function(error) {
        console.log('Error: ' + error);
        res.json();
    });
});

var curut2 = router.route('/');
curut2.post(function(req, res, next) {
    console.log(req.body);
    var entity = req.body;
    var entity2 = {
        'messageText': entity.messageText,
        'Receiver_id': entity.receiverid.id,
        'Sender_id': entity.senderid.id
    };
    entityMap.Insert(entity2).then(function(result) {
        console.log("inserted: " + result.affectedRows);
        res.json();
    }).catch(function(error) {
        concole.log(error);
        res.json();
    });
});

var curut3 = router.route('/getMessagesFromOneSender/:receiver_id/:sender_id');
curut3.get(function(req, res, next) {
    var rece_id = req.params.receiver_id;
    var send_id = req.params.sender_id;

    var str = 'SELECT m.id, m.isRead, m.isDeleted, m.messageText, m.timeSent, ' + 
    'r.id AS rid, r.occupation AS roccupation, r.pass AS rpass, r.username AS rusername, ' + 
    's.id AS sid, s.occupation AS soccupation, s.pass AS spass, s.username AS susername ' +
    'FROM t_messages m ' +
    'INNER JOIN t_users r ON r.id = m.Receiver_id ' +
    'INNER JOIN t_users s ON s.id = m.Sender_id WHERE m.Receiver_id = ' + rece_id + 
    ' AND m.Sender_id = ' + send_id;
    var query = session.executeSql(str);
    query.then(function(result) {
        //console.log(result);
        var jsonresult = toJsonTree(result);
        //console.log(jsonresult);
        res.json(jsonresult);
    }).catch(function(error) {
        console.log('Error: ' + error);
        res.json();
    });

});

var curut4 = router.route('/getMessagesFromAll/:receiver_id');
curut4.get(function(req, res, next) {
    var rece_id = req.params.receiver_id;

    /*var rece_id = req.params.receiver_id;*/
    var str = 'SELECT m.id, m.isRead, m.isDeleted, m.messageText, m.timeSent, ' + 
    'r.id AS rid, r.occupation AS roccupation, r.pass AS rpass, r.username AS rusername, ' + 
    's.id AS sid, s.occupation AS soccupation, s.pass AS spass, s.username AS susername ' +
    'FROM t_messages m ' +
    'INNER JOIN t_users r ON r.id = m.Receiver_id ' +
    'INNER JOIN t_users s ON s.id = m.Sender_id WHERE m.Receiver_id = ' + rece_id;
    var query = session.executeSql(str);
    //var query = session.query(entityMap).select();
    query.then(function(result) {
        //console.log(result);
        var jsonresult = toJsonTree(result);
        //console.log(jsonresult);
        res.json(jsonresult);
    }).catch(function(error) {
        console.log('Error: ' + error);
        res.json();
    });
});



var curut5 = router.route('/setMessageToIsRead');
curut5.post(function(req, res, next) {
    var id = req.body.id;
    var str = "UPDATE t_messages SET isRead = true WHERE id = " + id;
    var query = session.executeSql(str);
    query.then(function(result) {
        console.log("updated: " + id);
        res.json();
    }).catch(function(err) {
        console.log('Error: ' + error);
        res.json();
    });
});


//now we need to apply our router here
app.use('/SocialmediaMicro/entities.tMessages', router);
const PORT = process.env.PORT || 8080;
//start Server
var server = app.listen(PORT, function() {
    console.log("Listening to port %s", server.address().port);
});