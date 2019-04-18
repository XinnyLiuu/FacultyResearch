'use strict';
const db = require('../db/mysql');

/**
 * Controller for all equipment related data and views
 */
 module.exports = {
     getAllEquipment
 }

 /**
  * GET /
  * Renders the page for all equipments
  */
 function getAllEquipment(req, res) {
    db.select( "select * from equipment" )
    .then(data => {
        const equipment_data = {};
        let fields = [];
        let values = [];

        // Get the field names
        for(let field in data[0]) {
            fields.push( field );
        }

        // Get the values
        for(let i=0; i<data.length; i++) {
            let value = {};

            for(let field in data[i]) {
                value[field] = data[i][field];
            }

            values.push( value );
        }

        // Set up object
        equipment_data["fields"] = fields;
        equipment_data["values"] = values;

        return res.render('equipment.hbs', {
            equipment_data: equipment_data
        });
    })
    .catch(err => {
        console.log( err );
    });
 }