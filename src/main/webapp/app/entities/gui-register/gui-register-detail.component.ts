import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { GuiRegister } from './gui-register.model';
import { GuiRegisterService } from './gui-register.service';

@Component({
    selector: 'jhi-gui-register-detail',
    templateUrl: './gui-register-detail.component.html'
})
export class GuiRegisterDetailComponent implements OnInit, OnDestroy {

    guiRegister: GuiRegister;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private guiRegisterService: GuiRegisterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGuiRegisters();
    }

    load(id) {
        this.guiRegisterService.find(id)
            .subscribe((guiRegisterResponse: HttpResponse<GuiRegister>) => {
                this.guiRegister = guiRegisterResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGuiRegisters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'guiRegisterListModification',
            (response) => this.load(this.guiRegister.id)
        );
    }
}
