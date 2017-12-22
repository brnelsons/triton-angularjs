var app = angular.module('app', []);
const API_GAME_PATH = 'http://localhost:8080/api/v1/game/';
const API_SETTINGS_PATH = 'http://localhost:8080/api/v1/settings/';

app.controller('controller', function ($scope, $filter, $http, $window) {

    /**
     * FIELDS
     */
    $scope.alert = null;//object
    $scope.commands = [];//array
    $scope.allUsers = [];//array
    $scope.statuses = {};//dictionary

    /**
     * GAMES
     */
    $scope.getAllGames = function () {
        $http.get(API_GAME_PATH)
            .then(function (response) {
                $scope.allGames = response.data;
            });
    };
    $scope.getAllGames();

    function pushGame(endpoint) {
        if ($scope.commands !== []) {
            $scope.game.commands = $scope.commands;
            console.log($scope.commands);
            console.log($scope.game.commands);
        }
        $http.post(API_GAME_PATH + endpoint + '/', $scope.game)
            .then(
                function () {
                    $scope.showAlert('SUCCESS',
                        "SUCCESS",
                        'Successfully added/updated server!')
                },
                function (response) {
                    $scope.showAlert('DANGER',
                        "ERROR",
                        'Error adding/updating server! Please contact your system admin!' + response.error);
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
        var deleteGameServer  = $window.confirm('Are you sure you want to delete this server?');
        if (deleteGameServer) {
            $http.post(API_GAME_PATH + 'delete/', $scope.game)
                .then(
                    function () {
                        $scope.showAlert('SUCCESS',
                            "SUCCESS",
                            "Successfully deleted server!");
                    },
                    function (response) {
                        $scope.showAlert('DANGER',
                            "ERROR",
                            "Error deleting server! Please contact your system admin!" + response.error);
                    }
                );
        }
    };

    /**
     * ********** COMMANDS **********
     */
    $scope.addCommand = function () {
        $scope.commands.push({name: '', type: '', exe: '', resultComparatorType: null, expectedResult: null})
    };

    $scope.removeCommand = function (cmd) {
        var index = $scope.commands.indexOf(cmd);
        $scope.commands.splice(index, 1);
    };

    $scope.runCommand = function (game, command) {
        $http.post(API_GAME_PATH + game.gameName + '/' + game.serverName + '/runCommand/' + command.name + '/')
            .then(
                function (response) {
                    if (response.data === true) {
                        $scope.showAlert('SUCCESS',
                            "SUCCESS",
                            "Successfully sent command!");
                    } else {
                        $scope.showAlert('DANGER',
                            "ERROR",
                            "Error its already running!");
                    }
                },
                function (response) {
                    $scope.showAlert('DANGER',
                        "ERROR",
                        "Error sending command!" + response.error);
                }
            );
    };

    /**
     * ********** USERS **********
     */
    $scope.addUser = function (username) {
        $scope.allUsers.push({username: username, role: 'ADMIN', password: null});
        $scope.username = null;
    };

    $scope.removeUser = function (user) {
        var index = $scope.allUsers.indexOf(user);
        $scope.allUsers.splice(index, 1);
    };

    $scope.getAllUsers = function () {
        $http.get(API_SETTINGS_PATH + "/users")
            .then(function (response) {
                $scope.allUsers = response.data;
            });
    };

    $scope.submitUsers = function () {
        $http.post(API_SETTINGS_PATH + "/users/save", $scope.allUsers)
            .then(
                function (response) {
                    if (response.data === true) {
                        $scope.showAlert('SUCCESS',
                            "SUCCESS",
                            "Successfully saved settings!");
                    } else {
                        $scope.showAlert('DANGER',
                            "ERROR",
                            "Error saving settings!");
                    }
                },
                function (response) {
                    $scope.showAlert('DANGER',
                        "ERROR",
                        "Error saving settings!" + response.error);
                }
            );
    };

    /**
     * ********** ALERTS **********
     */
    $scope.showAlert = function (type, strong, text) {
        $scope.alert = {type: type, strong: strong, text: text};
    };

    $scope.getLog = function (gameName, serverName) {
        $http.get(API_GAME_PATH + gameName + '/' + serverName + '/jobs')
            .then(function (response) {
                $scope.resultCommands = response.data;
            });
    };

    /**
     * ********** SERVER STATUS **********
     */
    $scope.getServerStatus = function (gameName, serverName) {
        $http.get(API_GAME_PATH + gameName + '/' + serverName + '/status')
            .then(function (response) {
                $scope.statuses[gameName + '/' + serverName] = response.data;
            });
    };
    setInterval(function () {
        for (var key in $scope.statuses) {
            if ($scope.statuses.hasOwnProperty(key)) {
                var keySplit = key.split("/");
                $scope.getServerStatus(keySplit[0], keySplit[1]);
            }
        }
    }, 10 * 1000);

    /**
     * ********** SETTINGS **********
     */

    $scope.addConfig = function (configName) {
        $scope.allSettings.push({name: configName, value: null});
        $scope.configName = null;
    };

    $scope.removeConfig = function (config) {
        var index = $scope.allSettings.indexOf(config);
        $scope.allSettings.splice(index, 1);
    };
    $scope.getAllSettings = function () {
        $http.get(API_SETTINGS_PATH + "/config")
            .then(function (response) {
                $scope.allSettings = response.data;
            });
    };

    $scope.submitSettings = function () {
        $http.post(API_SETTINGS_PATH + "/config/save", $scope.allSettings)
            .then(
                function (response) {
                    if (response.data === true) {
                        $scope.showAlert('SUCCESS',
                            "SUCCESS",
                            "Successfully saved settings!");
                    } else {
                        $scope.showAlert('DANGER',
                            "ERROR",
                            "Error saving settings!");
                    }
                },
                function (response) {
                    $scope.showAlert('DANGER',
                        "ERROR",
                        "Error saving settings!" + response.error);
                }
            );
    };

});