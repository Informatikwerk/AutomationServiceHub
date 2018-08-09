/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { LibraryRegistryComponent } from '../../../../../../main/webapp/app/entities/library-registry/library-registry.component';
import { LibraryRegistryService } from '../../../../../../main/webapp/app/entities/library-registry/library-registry.service';
import { LibraryRegistry } from '../../../../../../main/webapp/app/entities/library-registry/library-registry.model';

describe('Component Tests', () => {

    describe('LibraryRegistry Management Component', () => {
        let comp: LibraryRegistryComponent;
        let fixture: ComponentFixture<LibraryRegistryComponent>;
        let service: LibraryRegistryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [LibraryRegistryComponent],
                providers: [
                    LibraryRegistryService
                ]
            })
            .overrideTemplate(LibraryRegistryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LibraryRegistryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryRegistryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LibraryRegistry(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.libraryRegistries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
