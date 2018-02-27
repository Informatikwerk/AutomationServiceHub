(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('GuiController', GuiController);

    GuiController.$inject = ['DataUtils', 'Gui'];

    function GuiController(DataUtils, Gui) {

        var vm = this;

        vm.guis = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Gui.query(function(result) {
                vm.guis = result;
                vm.searchQuery = null;
            });
        }
    }
})();
