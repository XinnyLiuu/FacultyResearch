'use strict';
const mysql = require('./db/mysql');
const bodyParser = require('body-parser');
const exphbs  = require('express-handlebars');
const express = require('express');
const morgan = require('morgan');
const http = require('http');
const path = require('path');

/**
 * Web server configurations
 */

// Controllers (route handlers)
const equipmentController = require("./controllers/equipmentController");

// Create express server
const app = express();

// Set up Handlebars templating
const hbs = exphbs.create({
    defaultLayout: "layout.hbs"
}); 
app.engine('hbs', hbs.engine);
app.set("view engine", "hbs"); 

// Server configs
app.use(bodyParser.json()); // Support application/json post data
app.use(bodyParser.urlencoded({ extended: true })); // Support application/x-www-form-urlencoded post data
app.use(express.static(
    path.join(__dirname, 'public')
));

// Terminal output layout
app.use(morgan(':date[iso] :status :method :url [:response-time ms] :remote-addr')); 

/**
 * Routes
 */
app.get("/", equipmentController.getAllEquipment);

// Connect to DB and start Express server
mysql.connect()
.then(success => {
    console.log( success );

    // Start Express server
    const httpServer = http.createServer( app );
    const httpPort = 8000;
    httpServer.listen( httpPort );
    console.log(`Express server listening on port ${httpPort}`);
})
.catch(err => {
    console.log(err);
    process.exit( 1 );
});