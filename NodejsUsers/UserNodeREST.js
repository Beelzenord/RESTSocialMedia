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
    // sometimes localhost, sometimes 127.0.0.1
    host: "127.0.0.1",
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

var userMap = session.tableMap('T_Users')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id') 
.columnMap('occupation', 'occupation')
.columnMap('pass', 'pass')
.columnMap('username', 'username');

/*** not used for TUsers ***/
function toJsonTree(result) {
    var tUserss = [];
    for (i = 0; i < result.length; i++) {
        jsonBranch(tUserss, result[i], i);
    }
    //console.log("tmess: ");
    //console.log(tUserss);
    return tUserss;
}

/*** not used for TUsers ***/
function jsonBranch(tUserss, row, num) {
    tUserss.push( 
        tUsers = {
            id : row.id, 
            isRead : row.isRead,
            isDeleted : row.isDeleted,
            messageText : row.messageText,
            timeSent : row.timeSent,
            receiverid : {
                id : row.rid,
                occupation : row.roccupation,
                pass : row.rpass,
                username : row.rusername
            },
            senderid : {
                id : row.sid,
                occupation : row.soccupation,
                pass : row.spass,
                username : row.susername
            }
        }
    );
}

app.get('/',function(req,res){
    res.send('Welcome to users');
});
    
//RESTful route
var router = express.Router(); 

router.use(function(req, res, next) {
    console.log(req.method, req.url);
    next();
});

var curut = router.route('/:id');
curut.get(function(req,res,next){
    var id = req.params.id;
    var query = session.query(userMap).
        where(
            userMap.id.Equal(id)
        );
    query.then(function(result){
        res.json(result[0]);
    }).catch(function(error){
        console.log('Error: ' + error);
    });
});

curut.post(function(req,res,next) {
    //console.log(req.body);
    var entity = req.body;
    var entity2 = {
        'occupation' : entity.occupation,
        'pass' : entity.pass, 
        'username' : entity.username, 
    };
    entityMap.Insert(entity2).then(function(result) {
        console.log("inserted: " + result.affectedRows);
        res.json();
    }).catch(function (error) {
        res.json();
    });
});

var curut2 = router.route('/login/:username/:password');
curut2.get(function(req,res,next) {
    var username = req.params.username;
    var password = req.params.password;

    var query = session.query(userMap)
    .where(
        userMap.pass.Equal(password) 
    );
    
    query.then(function(result) {
        res.json(result[0]);
    }).catch(function(error) {
        console.log('Error: ' + error);
        res.json();
    });
});



var curut3 = router.route('/getUsersByContains/:searchString');
curut3.get(function(req,res,next) {
    var searchString = req.params.searchString;

    var str = "SELECT * FROM T_Users WHERE username LIKE '%"+searchString+"%'";
    var query = session.executeSql(str);
    query.then(function(result) {
        res.json(result);
    }).catch(function(error) {
        console.log('Error: ' + error);
        res.json();
    });
});


//now we need to apply our router here
app.use('/SocialmediaMicro/entities.tusers', router);

//start Server
var server = app.listen(3002,function(){
    console.log("Listening to port %s",server.address().port);
});



