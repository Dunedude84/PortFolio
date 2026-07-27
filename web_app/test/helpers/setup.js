const { MongoMemoryServer } = require('mongodb-memory-server');
const mongoose = require('mongoose');
const request = require('supertest');

let mongoServer;
let app;

async function setup(env = {}) {
    mongoServer = await MongoMemoryServer.create();
    process.env.MONGODB_URI = mongoServer.getUri();
    process.env.SESSION_SECRET = env.sessionSecret || 'test-secret';
    process.env.ADMIN_USERNAME = env.adminUsername || 'admin';
    process.env.APP_PASSWORD = env.adminPassword || 'password';
    process.env.GUEST_USERNAME = env.guestUsername || 'guest';
    process.env.GUEST_PASSWORD = env.guestPassword || 'guestpass';

    // Clear cache so each test file uses the environment values set above
    const serverPath = require.resolve('../../server');
    delete require.cache[serverPath];

    const server = require('../../server');
    app = server;
    await server.dbPromise;

    return { app, agent: request.agent(app) };
}

async function teardown() {
    if (mongoose.connection.readyState !== 0) {
        try {
            await mongoose.connection.dropDatabase();
        } catch (err) {
            // Ignore if already dropped
        }
        await mongoose.connection.close();
    }
    if (mongoServer) {
        await mongoServer.stop();
    }
}

async function clearCollections() {
    if (mongoose.connection.readyState === 1) {
        const collections = await mongoose.connection.db.collections();
        for (const collection of collections) {
            await collection.deleteMany({});
        }
    }
}

module.exports = { setup, teardown, clearCollections };
