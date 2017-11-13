var app = angular.module('app', []);
const API_GAME_PATH = 'http://localhost:8080/api/v1/game/';
app.controller('controller', function ($scope, $filter, $http, $location) {

    $scope.getAllGames = function () {
        $http.get(API_GAME_PATH)
            .then(function (response) {
                $scope.allGames = response.data;
            });
    };
    $scope.getAllGames();
    $scope.commands = [];
    $scope.addCommand = function () {
        $scope.commands.push({name: '', type: '', exe: ''})
    };
    $scope.addCommand();
    // $scope.removeCommand = function(command){
    //     $scope.commands.
    // };

    $scope.submitAddGame = function () {
        var data = {
            gameName: $scope.gameName,
            imageUrl: $scope.imageUrl,
            serverName: $scope.serverName,
            serverUrl: $scope.serverUrl,
            commands: $scope.commands
        };
        $http.post(API_GAME_PATH + 'create/', data)
            .then(
                function () {
                    $location.path('/')
                },
                function () {
                    console.log("ERROR")
                }
            );
    };

    $scope.getGame = function(gameName, serverName){
        $http.get(API_GAME_PATH + '/' + gameName + '/' + serverName)
            .then(function(response){
                $scope.game = response.data;
            });
    };
});