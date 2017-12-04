var express = require('express'),
path = require('path'),
bodyParser = require('body-parser'),
app = express(),
expressValidator = require('express-validator');
//require('body-parser-xml')(bodyParser);

//app.set('views','./views');

app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true })); //support x-www-form-urlencoded
app.use(bodyParser.json());
app.use(expressValidator());

var dbconfig = {
    host: "localhost",
    user: "root",
    password: "root",
    database: "socialmedia",
    typeCast: function castField( field, useDefaultTypeCasting ) {
        // We only want to cast bit fields that have a single-bit in them. If the field
        // has more than one bit, then we cannot assume it is supposed to be a Boolean.
        if ( ( field.type === "BIT" ) && ( field.length === 1 ) ) {
            var bytes = field.buffer();
            // A Buffer in Node represents a collection of 8-bit unsigned integers.
            // Therefore, our single "bit field" comes back as the bits '0000 0001',
            // which is equivalent to the number 1.
            return( bytes[ 0 ] === 1 );
        }

        return( useDefaultTypeCasting() );
    }
};

var jsORM = require('js-hibernate');
var session = jsORM.session(dbconfig);

/*MySql connection*/
var connection  = require('express-myconnection'),
mysql = require('mysql');

app.use(
    connection(mysql,{
        host     : 'localhost',
        user     : 'root',
        password : 'root',
        database : 'socialmedia',
        debug    : false, //set true if you wanna see debug logger
        typeCast: function castField( field, useDefaultTypeCasting ) {
            // We only want to cast bit fields that have a single-bit in them. If the field
            // has more than one bit, then we cannot assume it is supposed to be a Boolean.
            if ( ( field.type === "BIT" ) && ( field.length === 1 ) ) {
                var bytes = field.buffer();
                // A Buffer in Node represents a collection of 8-bit unsigned integers.
                // Therefore, our single "bit field" comes back as the bits '0000 0001',
                // which is equivalent to the number 1.
                return( bytes[ 0 ] === 1 );
            }
            return( useDefaultTypeCasting() );
        }
    },'request')
);

/*var User = sequelize.define('T_User', {
    id: {

    }, 
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: 'compositeIndex'
    },
    pass: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: 'compositeIndex'
    }, 
    occupation: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: 'compositeIndex'
    }*/

var userMap = session.tableMap('T_Users')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id') 
.columnMap('occupation', 'occupation')
.columnMap('pass', 'pass')
.columnMap('username', 'username');


var entityMap = session.tableMap('T_Messages')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id') 
.columnMap('isRead', 'isRead')
.columnMap('isDeleted', 'isDeleted')
.columnMap('messageText', 'messageText')
.columnMap('timeSent', 'timeSent')
.columnMap('Receiver_id', 'Receiver_id')
.columnMap('Sender_id', 'Sender_id');  

app.get('/',function(req,res){
    res.send('Welcome to messages');
});
    
//RESTful route
var router = express.Router(); 

router.use(function(req, res, next) {
    console.log(req.method, req.url);
    next();
});

var curut = router.route('');

curut.get(function(req,res,next){

    /*var query = session.query(entityMap).select();

    query.then(function(result){
        console.log(result); // array with result 
        res.json(result);
    }).catch(function(error){
        console.log('Error: ' + error);
    });*/
});

curut.post(function(req,res,next) {
    //:TODO create message
});

var curut2 = router.route('/getMessagesFromOneSender/:receiver_id/:sender_id');

curut2.get(function(req,res,next) {
    
    /*var query = session.query(entityMap)
    .where(entityMap.receiverid.Equal(receiver_id))
    .where(entityMap.senderid.Equal(sender_id)
    );
    query.then(function(result) {
        console.log(result);
        res.json(result);
    }).catch(function(error) {
        console.log('Error: ' + error);
    });*/
    /*
    @GET
    @Path("getMessagesFromOneSender/{receiver_id}/{sender_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<TMessages> getMessagesFromOneSender(@PathParam("receiver_id") Long receiver_id, @PathParam("sender_id") Long sender_id) {
        em = getEntityManager();
        Query q = em.createNamedQuery("TMessages.findFromOneSender");
        q.setParameter("Receiver_id", receiver_id);
        q.setParameter("Sender_id", sender_id);
        Collection<TMessages> tmp = q.getResultList();
        return tmp;
    }*/
});

var tree;
function toTree(ta) {
    //console.log(ta[0]);
    treeBuilder(ta[0]);
    //for(i = 0; i < ta.length; i++) {
    //    treeBuilder(ta[i]);
    //}
}

function treeBuilder(row) {
    var receiverTmp;
    var senderTmp;
    var query2 = session.query(userMap).where(userMap.id.Equal(row.Receiver_id));
    query2.then(function(result_rec) {
        console.log(result_rec);
        receiverTmp = result_rec[0];
    }).catch(function(error) {
        console.log('Error: ' + error);
    });
    var query3 = session.query(userMap).where(userMap.id.Equal(row.Sender_id));
    query3.then(function(result_send) {
        console.log(result_send);
        senderTmp = result_send[0];
    }).catch(function(error) {
        console.log('Error: ' + error);
    });
    console.log("lkj");
    console.log(row);
    console.log("done");



    
    tree = [{ "id": row.id, 
             "isRead": row.isRead, 
             isDeleted: row.isDeleted,
             messageText: row.messageText, 
             timeSent: row.timeSent, 
             receiver: [ {id: receiverTmp.id,
                          occupation: receiverTmp.occupation, 
                          pass: receiverTmp.pass, 
                          username: receiverTmp.username } ], 
             sender:   [ {id: senderTmp.id,
                          occupation: senderTmp.occupation, 
                          pass: senderTmp.pass, 
                          username: senderTmp.username } ]
             }]
             console.log("the");
             console.log(tree);
             console.log("tree");
}

var curut3 = router.route('/getMessagesFromAll/:receiver_id');

curut3.get(function(req,res,next) {
    var rece_id = req.params.receiver_id;
    /*connection.query({
        query: 'SELECT * from `T_Messages` WHERE Receiver_id = ' + rece_id,
        nestTables: true
    }, function(err, results) {
        if (err) {
            console.log(err)
        } else {
            console.log(results);
            res.json(results);
        }
    });*/

    
    
    /*req.getConnection(function(err,conn){
        
        if (err) 
            return next("Cannot Connect");
            //SELECT d.name, e.name, e.email, ... FROM deparments d INNER JOIN employees e ON d.id = e.department_id.
        var str = 'SELECT m.id, m.isRead, m.isDeleted, m.messageText, m.timeSent, u FROM T_Messages m INNER ' 
                    + 'JOIN T_Users u ON u.id = m.Receiver_id WHERE m.Receiver_id = 1';

        var str2 = 'SELECT m FROM T_Messages m WHERE m.receiverid = 1';
        var query = conn.query(str,function(err,rows){

            if(err){
                console.log(err);
                return next("Mysql error, check your query");
            }
            console.log(rows);
            res.json(rows);
            //res.render('user',{title:"RESTful Crud Example",data:rows});

            });

    });*/

    var json_hierarchy_factory = require('json-hierarchy-factory');


    /*var rece_id = req.params.receiver_id;
    var sql = 'SELECT * from `T_Messages` WHERE Receiver_id = ' + rece_id;
    var query = session.executeSql(sql);*/
    var query = session.query(entityMap).select();
    query.then(function(result) {
        console.log(result);

        //toTree(result);
        res.json(result[0]);
    }).catch(function(error) {
        console.log('Error: ' + error);
    });
});



var curut4 = router.route('/setMessageToIsRead/:entity');
curut4.post(function(req,res,next) {
    //:TODO update isread
    
});




//now we need to apply our router here
app.use('/SocialmediaMicro/entities.tmessages', router);

//start Server
var server = app.listen(3001,function(){
    console.log("Listening to port %s",server.address().port);
});

