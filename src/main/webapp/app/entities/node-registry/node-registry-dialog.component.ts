import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NodeRegistry } from './node-registry.model';
import { NodeRegistryPopupService } from './node-registry-popup.service';
import { NodeRegistryService } from './node-registry.service';

@Component({
    selector: 'jhi-node-registry-dialog',
    templateUrl: './node-registry-dialog.component.html'
})
export class NodeRegistryDialogComponent implements OnInit {

    nodeRegistry: NodeRegistry;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private nodeRegistryService: NodeRegistryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.nodeRegistry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nodeRegistryService.update(this.nodeRegistry));
        } else {
            this.subscribeToSaveResponse(
                this.nodeRegistryService.create(this.nodeRegistry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NodeRegistry>>) {
        result.subscribe((res: HttpResponse<NodeRegistry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NodeRegistry) {
        this.eventManager.broadcast({ name: 'nodeRegistryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-node-registry-popup',
    template: ''
})
export class NodeRegistryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodeRegistryPopupService: NodeRegistryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nodeRegistryPopupService
                    .open(NodeRegistryDialogComponent as Component, params['id']);
            } else {
                this.nodeRegistryPopupService
                    .open(NodeRegistryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
