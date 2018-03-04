(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('NodeMetaRegisterController', NodeMetaRegisterController);

    NodeMetaRegisterController.$inject = ['DataUtils', 'NodeMetaRegister'];

    function NodeMetaRegisterController(DataUtils, NodeMetaRegister) {

        var vm = this;

        vm.nodeMetaRegisters = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            NodeMetaRegister.query(function(result) {
                vm.nodeMetaRegisters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
