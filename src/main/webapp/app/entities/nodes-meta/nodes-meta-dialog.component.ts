import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { NodesMeta } from './nodes-meta.model';
import { NodesMetaPopupService } from './nodes-meta-popup.service';
import { NodesMetaService } from './nodes-meta.service';

@Component({
    selector: 'jhi-nodes-meta-dialog',
    templateUrl: './nodes-meta-dialog.component.html'
})
export class NodesMetaDialogComponent implements OnInit {

    nodesMeta: NodesMeta;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private nodesMetaService: NodesMetaService,
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
        if (this.nodesMeta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nodesMetaService.update(this.nodesMeta));
        } else {
            this.subscribeToSaveResponse(
                this.nodesMetaService.create(this.nodesMeta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NodesMeta>>) {
        result.subscribe((res: HttpResponse<NodesMeta>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NodesMeta) {
        this.eventManager.broadcast({ name: 'nodesMetaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-nodes-meta-popup',
    template: ''
})
export class NodesMetaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodesMetaPopupService: NodesMetaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nodesMetaPopupService
                    .open(NodesMetaDialogComponent as Component, params['id']);
            } else {
                this.nodesMetaPopupService
                    .open(NodesMetaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
