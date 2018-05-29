/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { GuisDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/guis/guis-delete-dialog.component';
import { GuisService } from '../../../../../../main/webapp/app/entities/guis/guis.service';

describe('Component Tests', () => {

    describe('Guis Management Delete Component', () => {
        let comp: GuisDeleteDialogComponent;
        let fixture: ComponentFixture<GuisDeleteDialogComponent>;
        let service: GuisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [GuisDeleteDialogComponent],
                providers: [
                    GuisService
                ]
            })
            .overrideTemplate(GuisDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GuisDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GuisService);
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
