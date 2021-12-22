if (process.env.NODE_ENV !== 'production') {
	import 'dotenv/config';
}


console.log("==========================");
console.log("=== AMBIENTE:", process.env.NODE_ENV);
console.log("==========================");


var AWS = require('aws-sdk');
var uuid = require('node-uuid');
var s3 = new AWS.S3();
var sts = new AWS.STS();
var bucketName = 'node-sdk-sample-' + uuid.v4();
var keyName = 'hello_world.txt';


s3.createBucket({ Bucket: bucketName }, function (err, data) {

	if (err) console.log("---- ERROR CREATE BUCKET ----", err);

	var params = { Bucket: bucketName, Key: keyName, Body: 'Hello World!' };
	s3.putObject(params, function (err, data) {
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