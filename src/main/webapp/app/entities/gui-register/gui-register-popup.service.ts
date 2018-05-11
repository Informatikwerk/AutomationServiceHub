import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { GuiRegister } from './gui-register.model';
import { GuiRegisterService } from './gui-register.service';

@Injectable()
export class GuiRegisterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private guiRegisterService: GuiRegisterService

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
                this.guiRegisterService.find(id)
                    .subscribe((guiRegisterResponse: HttpResponse<GuiRegister>) => {
                        const guiRegister: GuiRegister = guiRegisterResponse.body;
                        this.ngbModalRef = this.guiRegisterModalRef(component, guiRegister);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.guiRegisterModalRef(component, new GuiRegister());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    guiRegisterModalRef(component: Component, guiRegister: GuiRegister): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.guiRegister = guiRegister;
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
