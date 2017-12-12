var app = angular.module('app', []);
const API_GAME_PATH = 'http://localhost:8080/api/v1/game/';
app.controller('controller', function ($scope, $filter, $http, $window) {
    $scope.alert = null;
    $scope.getAllGames = function () {
        $http.get(API_GAME_PATH)
            .then(function (response) {
                $scope.allGames = response.data;
            });
    };
    $scope.getAllGames();
    $scope.commands = [];
    $scope.addCommand = function () {
        $scope.commands.push({name: '', type: '', exe: '', resultComparatorType: null, expectedResult: null})
    };
    // $scope.addCommand();

    $scope.removeCommand = function (cmd) {
        var index = $scope.commands.indexOf(cmd);
        $scope.commands.splice(index, 1);
    };

    function pushGame(endpoint) {
        if ($scope.commands !== []) {
            $scope.game.commands = $scope.commands;
            console.log($scope.commands);
            console.log($scope.game.commands);
        }
        $http.post(API_GAME_PATH + endpoint + '/', $scope.game)
            .then(
                function () {
                    $scope.alert = {type: 'SUCCESS', strong: "SUCCESS", text: 'Successfully added/updated server!'};
                },
                function () {
                    $scope.alert = {
                        type: 'DANGER',
                        strong: "ERROR",
                        text: 'Error adding/updating server! Please contact your system admin!'
                    };
                }
            );
    }

    $scope.submitAddGame = function () {
        pushGame('create')
    };

    $scope.updateGame = function () {
        pushGame('update')
    };

    $scope.getGame = function (gameName, serverName) {
        $http.get(API_GAME_PATH + gameName + '/' + serverName + '/')
            .then(function (response) {
                $scope.game = response.data;
                $scope.commands = $scope.game.commands;
            });
    };

    $scope.deleteServer = function (game) {
        deleteGameServer = $window.confirm('Are you sure you want to delete this server?');
        if (deleteGameServer) {
            $http.post(API_GAME_PATH + 'delete/', $scope.game)
                .then(
                    function () {
                        $scope.alert = {type: 'SUCCESS', strong: "SUCCESS", text: 'Successfully deleted server!'};
                    },
                    function () {
                        $scope.alert = {
                            type: 'DANGER',
                            strong: "ERROR",
                            text: 'Error deleting server! Please contact your system admin!'
                        };
                    }
                );
        }
    };

    $scope.runCommand = function (game, command) {
        $http.post(API_GAME_PATH + game.gameName + '/' + game.serverName + '/runCommand/' + command.name + '/')
            .then(
                function (response) {
                    if (response.data === true) {
                        $scope.alert = {type: 'SUCCESS', strong: "SUCCESS", text: 'Successfully sent command!'};
                    } else {
                        $scope.alert = {type: 'DANGER', strong: "ERROR", text: 'Error tts already running!'};
                    }
                },
                function (response) {
                    $scope.alert = {type: 'DANGER', strong: "ERROR", text: 'Error sending command!'};
                }
            );
    };

    $scope.getLog = function (gameName, serverName) {
        $http.get(API_GAME_PATH + gameName + '/' + serverName + '/jobs')
            .then(function (response) {
                $scope.resultCommands = response.data;
            });
    };

    $scope.statuses = {};
    $scope.getServerStatus = function (gameName, serverName) {
        $http.get(API_GAME_PATH + gameName + '/' + serverName + '/status')
            .then(function (response) {
                $scope.statuses[gameName + '/' + serverName] = response.data;
            });
    };
    setInterval(function(){
        for(var key in $scope.statuses){
            if($scope.statuses.hasOwnProperty(key)){
                var keySplit = key.split("/");
                $scope.getServerStatus(keySplit[0], keySplit[1]);
            }
        }
    }, 10 * 1000);
});