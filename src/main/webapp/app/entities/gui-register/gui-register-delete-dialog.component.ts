import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GuiRegister } from './gui-register.model';
import { GuiRegisterPopupService } from './gui-register-popup.service';
import { GuiRegisterService } from './gui-register.service';

@Component({
    selector: 'jhi-gui-register-delete-dialog',
    templateUrl: './gui-register-delete-dialog.component.html'
})
export class GuiRegisterDeleteDialogComponent {

    guiRegister: GuiRegister;

    constructor(
        private guiRegisterService: GuiRegisterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.guiRegisterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'guiRegisterListModification',
                content: 'Deleted an guiRegister'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gui-register-delete-popup',
    template: ''
})
export class GuiRegisterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private guiRegisterPopupService: GuiRegisterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.guiRegisterPopupService
                .open(GuiRegisterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
