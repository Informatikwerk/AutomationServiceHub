/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { LibraryRegistryDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/library-registry/library-registry-delete-dialog.component';
import { LibraryRegistryService } from '../../../../../../main/webapp/app/entities/library-registry/library-registry.service';

describe('Component Tests', () => {

    describe('LibraryRegistry Management Delete Component', () => {
        let comp: LibraryRegistryDeleteDialogComponent;
        let fixture: ComponentFixture<LibraryRegistryDeleteDialogComponent>;
        let service: LibraryRegistryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [LibraryRegistryDeleteDialogComponent],
                providers: [
                    LibraryRegistryService
                ]
            })
            .overrideTemplate(LibraryRegistryDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LibraryRegistryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryRegistryService);
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
