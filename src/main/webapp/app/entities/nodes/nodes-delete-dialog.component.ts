import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Nodes } from './nodes.model';
import { NodesPopupService } from './nodes-popup.service';
import { NodesService } from './nodes.service';

@Component({
    selector: 'jhi-nodes-delete-dialog',
    templateUrl: './nodes-delete-dialog.component.html'
})
export class NodesDeleteDialogComponent {

    nodes: Nodes;

    constructor(
        private nodesService: NodesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nodesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nodesListModification',
                content: 'Deleted an nodes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nodes-delete-popup',
    template: ''
})
export class NodesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodesPopupService: NodesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nodesPopupService
                .open(NodesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
