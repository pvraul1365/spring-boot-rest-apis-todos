// mongo-init.js
db = db.getSiblingDB('tododb');
db.users.createIndex({ email: 1 }, { unique: true });
