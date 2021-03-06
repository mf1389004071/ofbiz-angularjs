package org.ofbiz.angularjs.example.controller;

function ExampleCommonDecoratorController($rootScope, $scope) {
    
    $rootScope.$on("ON_HTTP_RESPONSE_MESSAGE_RECEIVED", function(event, responseMessages) {
        $scope.alerts = responseMessages;
    });
    
    $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
        $scope.alerts = [];
    });
    
    $scope.$on('$viewContentLoading', function(event, viewConfig) {
    });
    
    $scope.$on('$viewContentLoaded', function(event) {
    });
    
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
}
