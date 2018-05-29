import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Guis } from './guis.model';
import { GuisService } from './guis.service';

@Injectable()
export class GuisPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private guisService: GuisService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.guisService.find(id)
                    .subscribe((guisResponse: HttpResponse<Guis>) => {
                        const guis: Guis = guisResponse.body;
                        this.ngbModalRef = this.guisModalRef(component, guis);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.guisModalRef(component, new Guis());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    guisModalRef(component: Component, guis: Guis): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.guis = guis;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
