if (process.env.NODE_ENV !== 'production') {
	import 'dotenv/config';
}


const knex = require('knex')({
	client: 'pg',
	version: '13',
	connection: {
		host: process.env.DB_HOST,
		user: process.env.DB_USER,
		password: process.env.DB_PASSWORD,
		database: process.env.DB_DATABASE,
		port: process.env.DB_PORT,
		ssl: process.env.DB_SSL == "true"
	}
});

module.exports = knex