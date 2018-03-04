(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('NodeMetaRegisterDetailController', NodeMetaRegisterDetailController);

    NodeMetaRegisterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'NodeMetaRegister'];

    function NodeMetaRegisterDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, NodeMetaRegister) {
        var vm = this;

        vm.nodeMetaRegister = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('automationServiceHubApp:nodeMetaRegisterUpdate', function(event, result) {
            vm.nodeMetaRegister = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
