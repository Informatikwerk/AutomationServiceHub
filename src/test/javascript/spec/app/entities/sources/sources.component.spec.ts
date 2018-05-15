/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { SourcesComponent } from '../../../../../../main/webapp/app/entities/sources/sources.component';
import { SourcesService } from '../../../../../../main/webapp/app/entities/sources/sources.service';
import { Sources } from '../../../../../../main/webapp/app/entities/sources/sources.model';

describe('Component Tests', () => {

    describe('Sources Management Component', () => {
        let comp: SourcesComponent;
        let fixture: ComponentFixture<SourcesComponent>;
        let service: SourcesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [SourcesComponent],
                providers: [
                    SourcesService
                ]
            })
            .overrideTemplate(SourcesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourcesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourcesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Sources(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sources[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
