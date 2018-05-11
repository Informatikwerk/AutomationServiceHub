import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { GuiRegister } from './gui-register.model';
import { GuiRegisterPopupService } from './gui-register-popup.service';
import { GuiRegisterService } from './gui-register.service';

@Component({
    selector: 'jhi-gui-register-dialog',
    templateUrl: './gui-register-dialog.component.html'
})
export class GuiRegisterDialogComponent implements OnInit {

    guiRegister: GuiRegister;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private guiRegisterService: GuiRegisterService,
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
        if (this.guiRegister.id !== undefined) {
            this.subscribeToSaveResponse(
                this.guiRegisterService.update(this.guiRegister));
        } else {
            this.subscribeToSaveResponse(
                this.guiRegisterService.create(this.guiRegister));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GuiRegister>>) {
        result.subscribe((res: HttpResponse<GuiRegister>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GuiRegister) {
        this.eventManager.broadcast({ name: 'guiRegisterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-gui-register-popup',
    template: ''
})
export class GuiRegisterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private guiRegisterPopupService: GuiRegisterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.guiRegisterPopupService
                    .open(GuiRegisterDialogComponent as Component, params['id']);
            } else {
                this.guiRegisterPopupService
                    .open(GuiRegisterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
