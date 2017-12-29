(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('NodeRegistryDetailController', NodeRegistryDetailController);

    NodeRegistryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NodeRegistry'];

    function NodeRegistryDetailController($scope, $rootScope, $stateParams, previousState, entity, NodeRegistry) {
        var vm = this;

        vm.nodeRegistry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('automationServiceHubApp:nodeRegistryUpdate', function(event, result) {
            vm.nodeRegistry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
