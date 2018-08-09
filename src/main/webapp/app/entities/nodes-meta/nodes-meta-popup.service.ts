import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { NodesMeta } from './nodes-meta.model';
import { NodesMetaService } from './nodes-meta.service';

@Injectable()
export class NodesMetaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private nodesMetaService: NodesMetaService

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
                this.nodesMetaService.find(id)
                    .subscribe((nodesMetaResponse: HttpResponse<NodesMeta>) => {
                        const nodesMeta: NodesMeta = nodesMetaResponse.body;
                        this.ngbModalRef = this.nodesMetaModalRef(component, nodesMeta);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nodesMetaModalRef(component, new NodesMeta());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nodesMetaModalRef(component: Component, nodesMeta: NodesMeta): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nodesMeta = nodesMeta;
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
