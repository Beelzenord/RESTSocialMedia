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

var graphMap = session.tableMap('t_graphs')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id', { isAutoIncrement: true })
.columnMap('Sender_id', 'Sender_id')
.columnMap('graphType', 'graphType');

var graphStatsMap = session.tableMap('t_graphstats')
// columnMap(object-name-property, table-name-property, optional-property-config) 
.columnMap('id', 'id', { isAutoIncrement: true })
.columnMap('Graph_id', 'Graph_id')
.columnMap('duration', 'duration')
.columnMap('label', 'label'); 


app.get( '/', function(req, res) {
    res.send('Welcome to graphs');
});

//RESTful route
var router = express.Router();

router.use(function(req, res, next) {
    console.log(req.method, req.url);
    next();
});



var curut7 = router.route('/savetodb');
curut7.post(function(req,res,next) {
    console.log("/savetodb");
    res.setHeader('Access-Control-Allow-Origin' , '*' );
    res.setHeader('Access-Control-Allow-Methods' , 'POST, GET' );
    console.log(req.body);
    var id = req.body.userid;
    var theChartType = req.body.theChartType;
    var httpAsync = req.body.theHttpAsync;
    var httpSync = req.body.theHttpSync;

    var entity = req.body;
    var entity2 = {
        'Sender_id' : id,
        'graphType' : theChartType
    };
    graphMap.Insert(entity2).then(function(result) {
        var graphid = result.insertId;
        for (var t = 0; t < httpAsync.length; t++) {
            var entity3 = {
                Graph_id: graphid, 
                duration: httpAsync[t].duration,
                label: httpAsync[t].label
            }
            graphStatsMap.Insert(entity3).then(function(result) {

            }).catch(function(error) {
                console.log("could not insert graphstats Async");
                console.log(error);
            });
        }
        for (var i = 0; i < httpSync.length; i++) {
            var entity4 = {
                Graph_id: graphid, 
                duration: httpSync[i].duration,
                label: httpSync[i].label
            }
            graphStatsMap.Insert(entity4).then(function(result) {

            }).catch(function(error) {
                console.log("could not insert graphstats Sync");
                console.log(error);
            });
        }

        
        res.end("saved to db");
    }).catch(function (error) {
        console.log("could not instert graph to db");
        console.log(error);
        res.end("error");
    });
});


var curut2 = router.route('/getgraph');
curut2.post(function(req,res,next) {
    console.log("/getgraph");
    res.setHeader('Access-Control-Allow-Origin' , '*' );
    res.setHeader('Access-Control-Allow-Methods' , 'POST, GET' );
    var id = req.body.id;

    var str = "SELECT * FROM t_graphs WHERE Sender_id LIKE '"+id+"'";
    var query = session.executeSql(str);
    query.then(function(result) {
        if (result.length <= 0 ) {
            res.end("no graphs");
            return;
        }
        var len = result.length;
        var graphid = result[len-1].id;
        var graphTypeStr = result[len-1].graphType;

        var str2 = "SELECT * FROM t_graphstats WHERE Graph_id LIKE '"+graphid+"'";
        var query2 = session.executeSql(str2);
        query2.then(function(result2) {
            var dataPoints = [];
            for (var i = 0; i < result2.length; i++) {
                dataPoints.push(
                    {duration: result2[i].duration},
                    {label: result2[i].label}
                )
            }
            var jsonresult = {
                graphType: graphTypeStr,
                dataPoints: dataPoints
            }
            console.log(jsonresult)
            var stringresult = JSON.stringify(jsonresult);
            res.json(stringresult);
        })

        
    }).catch(function (error) {
        console.log("could not get graph");
        console.log(error);
        res.end("could not get graph");
    });
});




//now we need to apply our router here
app.use('/graph', router);
const PORT = process.env.PORT || 8080;
//start Server
var server = app.listen(PORT, function() {
    console.log("Listening to port %s", server.address().port);
});