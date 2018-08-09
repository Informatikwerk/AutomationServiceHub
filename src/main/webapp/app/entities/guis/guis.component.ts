import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Guis } from './guis.model';
import { GuisService } from './guis.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-guis',
    templateUrl: './guis.component.html'
})
export class GuisComponent implements OnInit, OnDestroy {
guis: Guis[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private guisService: GuisService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.guisService.query().subscribe(
            (res: HttpResponse<Guis[]>) => {
                this.guis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGuis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Guis) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInGuis() {
        this.eventSubscriber = this.eventManager.subscribe('guisListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
