/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { SourcesDetailComponent } from '../../../../../../main/webapp/app/entities/sources/sources-detail.component';
import { SourcesService } from '../../../../../../main/webapp/app/entities/sources/sources.service';
import { Sources } from '../../../../../../main/webapp/app/entities/sources/sources.model';

describe('Component Tests', () => {

    describe('Sources Management Detail Component', () => {
        let comp: SourcesDetailComponent;
        let fixture: ComponentFixture<SourcesDetailComponent>;
        let service: SourcesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [SourcesDetailComponent],
                providers: [
                    SourcesService
                ]
            })
            .overrideTemplate(SourcesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourcesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourcesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Sources(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sources).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
