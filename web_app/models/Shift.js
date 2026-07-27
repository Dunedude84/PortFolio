const mongoose = require('mongoose');

const shiftSchema = new mongoose.Schema({
    employeeId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Employee',
        required: true
    },
    date: {
        type: String, // format YYYY-MM-DD
        required: true
    },
    department: { 
        type: String, 
        required: true, 
        enum: ['caisses_avant', 'plancher', 'cosmetiques', 'laboratoire'],
        default: 'caisses_avant' 
    },
    startTime: {
        type: String, // format HH:MM
        required: true
    },
    endTime: {
        type: String, // format HH:MM
        required: true
    },
    breakMinutes: {
        type: Number,
        default: 0
    },
    isFormation: {
        type: Boolean,
        default: false
    },
    hasStar: {
        type: Boolean,
        default: false
    }
});

shiftSchema.virtual('id').get(function(){
    return this._id.toHexString();
});

shiftSchema.set('toJSON', {
    virtuals: true,
    versionKey: false,
    transform: function (doc, ret) {
        delete ret._id;
        // Make sure employeeId is returned as string to match frontend expectations
        ret.employeeId = ret.employeeId.toString();
    }
});

module.exports = mongoose.model('Shift', shiftSchema);
