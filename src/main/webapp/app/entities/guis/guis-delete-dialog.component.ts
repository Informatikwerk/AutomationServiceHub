import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Guis } from './guis.model';
import { GuisPopupService } from './guis-popup.service';
import { GuisService } from './guis.service';

@Component({
    selector: 'jhi-guis-delete-dialog',
    templateUrl: './guis-delete-dialog.component.html'
})
export class GuisDeleteDialogComponent {

    guis: Guis;

    constructor(
        private guisService: GuisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.guisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'guisListModification',
                content: 'Deleted an guis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-guis-delete-popup',
    template: ''
})
export class GuisDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private guisPopupService: GuisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.guisPopupService
                .open(GuisDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
