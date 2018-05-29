/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesMetaDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta-delete-dialog.component';
import { NodesMetaService } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.service';

describe('Component Tests', () => {

    describe('NodesMeta Management Delete Component', () => {
        let comp: NodesMetaDeleteDialogComponent;
        let fixture: ComponentFixture<NodesMetaDeleteDialogComponent>;
        let service: NodesMetaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesMetaDeleteDialogComponent],
                providers: [
                    NodesMetaService
                ]
            })
            .overrideTemplate(NodesMetaDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesMetaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesMetaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
