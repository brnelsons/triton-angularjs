var app = angular.module('app', []);
app.controller('controller', function($scope, $filter, $http) {
    $scope.getAllGames = function(){
        $http.get('http://localhost:8080/api/v1/game/')
            .then(function(response){
                $scope.allGames = response.data;
            });
    };
    $scope.getAllGames();
    $scope.addGame = function(name, imageUrl, serverName, serverUrl){
        $http.post('http://localhost:8080/api/v1/game/add/', {
            gameName: name,
            imageUrl: imageUrl,
            serverName: serverName,
            serverUrl: serverUrl
        });
        console.log("posted " + data);
    }
});