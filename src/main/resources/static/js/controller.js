var app = angular.module('app', []);
app.controller('controller', function($scope, $filter, $http) {
    $scope.getAllGames = function(){
        $http.get('http://localhost:8080/api/v1/game/')
            .then(function(response){
                $scope.allGames = response.data;
            });
    };
    $scope.getAllGames();
});