// 'use strict';
//
//
// var stompClient = null;
//
//
// function connect() {
//
//     var socket = new SockJS('/ws');
//     stompClient = Stomp.over(socket);
//
//     stompClient.connect({}, onConnected, onError);
// }
//
// // Connect to WebSocket Server.
// connect();
//
// function onConnected() {
//     // Subscribe to the Public Topic
//     stompClient.subscribe('/topic/staffRoom', onMessageReceived);
// }
//
//
// function onError(error) {
//     connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//     connectingElement.style.color = 'red';
// }
//
//
//
// function onMessageReceived(payload) {
//     console.log("ok here");
//     //var element = document.getElementById("2");
//     //element.style.backgroundColor = "#00FF00";
//
//     var message = JSON.parse(payload.body);
//
//
//     if(message.type === 'CHAT') {
//         var element = document.getElementById(message.content);
//         element.style.backgroundColor = "#00FF00";
//     }
// }