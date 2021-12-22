import { Router } from 'express';
import os from 'os';


var AWS = require('aws-sdk');
var uuid = require('node-uuid');
var s3 = new AWS.S3();
var sts = new AWS.STS();
var bucketName = 'node-sdk-sample-' + uuid.v4();
var keyName = 'hello_world.txt';

const routes = Router();

const health = {
    app: "ms-caixa-backend",
    version: "1.0",
    status: true,
    hostname: os.hostname()
}

routes.get('/health', (req, res) => {


    s3.createBucket({Bucket: bucketName}, function(err, data) {

        if (err) console.log("---- ERROR CREATE BUCKET ----", err);

        var params = {Bucket: bucketName, Key: keyName, Body: 'Hello World!'};
        s3.putObject(params, function(err, data) {
            if (err)
                console.log("---- ERROR PUT OBJECT ----", err)
            else
                console.log("Successfully uploaded data to " + bucketName + "/" + keyName);
        });
    });

    sts.getCallerIdentity({}, function (err, data) {
        if (err) {
            console.log("Error", err);
        } else {
            console.log(JSON.stringify(data));
        }
    });

    res.send(health)
});

export default routes;