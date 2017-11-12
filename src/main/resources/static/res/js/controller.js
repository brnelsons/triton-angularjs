var app = angular.module('app', []);
app.controller('controller', function ($scope, $filter, $http, $location) {

    $scope.getAllGames = function () {
        $http.get('http://localhost:8080/api/v1/game/')
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
        $http.post('http://localhost:8080/api/v1/game/add/', data)
            .then(
                function () {
                    $location.path('/')
                },
                function () {
                    console.log("ERROR")
                }
            );
    }
});