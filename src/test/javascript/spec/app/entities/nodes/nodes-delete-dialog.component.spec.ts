/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/nodes/nodes-delete-dialog.component';
import { NodesService } from '../../../../../../main/webapp/app/entities/nodes/nodes.service';

describe('Component Tests', () => {

    describe('Nodes Management Delete Component', () => {
        let comp: NodesDeleteDialogComponent;
        let fixture: ComponentFixture<NodesDeleteDialogComponent>;
        let service: NodesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesDeleteDialogComponent],
                providers: [
                    NodesService
                ]
            })
            .overrideTemplate(NodesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesService);
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
