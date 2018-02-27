(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('GuiDetailController', GuiDetailController);

    GuiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Gui'];

    function GuiDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Gui) {
        var vm = this;

        vm.gui = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('automationServiceHubApp:guiUpdate', function(event, result) {
            vm.gui = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
