/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { LibraryRegistryDetailComponent } from '../../../../../../main/webapp/app/entities/library-registry/library-registry-detail.component';
import { LibraryRegistryService } from '../../../../../../main/webapp/app/entities/library-registry/library-registry.service';
import { LibraryRegistry } from '../../../../../../main/webapp/app/entities/library-registry/library-registry.model';

describe('Component Tests', () => {

    describe('LibraryRegistry Management Detail Component', () => {
        let comp: LibraryRegistryDetailComponent;
        let fixture: ComponentFixture<LibraryRegistryDetailComponent>;
        let service: LibraryRegistryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [LibraryRegistryDetailComponent],
                providers: [
                    LibraryRegistryService
                ]
            })
            .overrideTemplate(LibraryRegistryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LibraryRegistryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryRegistryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LibraryRegistry(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.libraryRegistry).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
