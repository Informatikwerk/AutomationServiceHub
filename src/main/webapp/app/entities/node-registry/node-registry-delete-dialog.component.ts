import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NodeRegistry } from './node-registry.model';
import { NodeRegistryPopupService } from './node-registry-popup.service';
import { NodeRegistryService } from './node-registry.service';

@Component({
    selector: 'jhi-node-registry-delete-dialog',
    templateUrl: './node-registry-delete-dialog.component.html'
})
export class NodeRegistryDeleteDialogComponent {

    nodeRegistry: NodeRegistry;

    constructor(
        private nodeRegistryService: NodeRegistryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nodeRegistryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nodeRegistryListModification',
                content: 'Deleted an nodeRegistry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-node-registry-delete-popup',
    template: ''
})
export class NodeRegistryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodeRegistryPopupService: NodeRegistryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nodeRegistryPopupService
                .open(NodeRegistryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
