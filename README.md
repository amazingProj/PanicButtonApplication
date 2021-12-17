

```
const express = require('express'); //requires express module
const socket = require('socket.io'); //requires socket.io module
const app = express();
var PORT = process.env.PORT || 3000;
const server = app.listen(3000); //hosts server on localhost:3000

console.log('Server is running');
const io = socket(server);

//Socket.io Connection------------------
io.on('connection', (socket) => {

    console.log("New socket connection: " + socket.id)


    socket.on('macAddress', (data) => {
        console.log(data);
    })

    
    socket.on('wifiInformation', (data) => {
        console.log(socket.connected);
        console.log(data); 
    })

    socket.on('panic-signal', (data) => {
        console.log("emergency signal received " + data + "\n")
    })
}
```
