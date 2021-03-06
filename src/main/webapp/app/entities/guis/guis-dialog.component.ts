import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Guis } from './guis.model';
import { GuisPopupService } from './guis-popup.service';
import { GuisService } from './guis.service';

@Component({
    selector: 'jhi-guis-dialog',
    templateUrl: './guis-dialog.component.html'
})
export class GuisDialogComponent implements OnInit {

    guis: Guis;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private guisService: GuisService,
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
        if (this.guis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.guisService.update(this.guis));
        } else {
            this.subscribeToSaveResponse(
                this.guisService.create(this.guis));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Guis>>) {
        result.subscribe((res: HttpResponse<Guis>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Guis) {
        this.eventManager.broadcast({ name: 'guisListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-guis-popup',
    template: ''
})
export class GuisPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private guisPopupService: GuisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.guisPopupService
                    .open(GuisDialogComponent as Component, params['id']);
            } else {
                this.guisPopupService
                    .open(GuisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
