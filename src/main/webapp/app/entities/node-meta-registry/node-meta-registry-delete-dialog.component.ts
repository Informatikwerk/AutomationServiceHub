import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NodeMetaRegistry } from './node-meta-registry.model';
import { NodeMetaRegistryPopupService } from './node-meta-registry-popup.service';
import { NodeMetaRegistryService } from './node-meta-registry.service';

@Component({
    selector: 'jhi-node-meta-registry-delete-dialog',
    templateUrl: './node-meta-registry-delete-dialog.component.html'
})
export class NodeMetaRegistryDeleteDialogComponent {

    nodeMetaRegistry: NodeMetaRegistry;

    constructor(
        private nodeMetaRegistryService: NodeMetaRegistryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nodeMetaRegistryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nodeMetaRegistryListModification',
                content: 'Deleted an nodeMetaRegistry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-node-meta-registry-delete-popup',
    template: ''
})
export class NodeMetaRegistryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodeMetaRegistryPopupService: NodeMetaRegistryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nodeMetaRegistryPopupService
                .open(NodeMetaRegistryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
