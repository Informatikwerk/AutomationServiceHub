import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { NodeMetaRegistry } from './node-meta-registry.model';
import { NodeMetaRegistryPopupService } from './node-meta-registry-popup.service';
import { NodeMetaRegistryService } from './node-meta-registry.service';

@Component({
    selector: 'jhi-node-meta-registry-dialog',
    templateUrl: './node-meta-registry-dialog.component.html'
})
export class NodeMetaRegistryDialogComponent implements OnInit {

    nodeMetaRegistry: NodeMetaRegistry;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private nodeMetaRegistryService: NodeMetaRegistryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.nodeMetaRegistry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nodeMetaRegistryService.update(this.nodeMetaRegistry));
        } else {
            this.subscribeToSaveResponse(
                this.nodeMetaRegistryService.create(this.nodeMetaRegistry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NodeMetaRegistry>>) {
        result.subscribe((res: HttpResponse<NodeMetaRegistry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NodeMetaRegistry) {
        this.eventManager.broadcast({ name: 'nodeMetaRegistryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-node-meta-registry-popup',
    template: ''
})
export class NodeMetaRegistryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodeMetaRegistryPopupService: NodeMetaRegistryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nodeMetaRegistryPopupService
                    .open(NodeMetaRegistryDialogComponent as Component, params['id']);
            } else {
                this.nodeMetaRegistryPopupService
                    .open(NodeMetaRegistryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
