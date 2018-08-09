/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { GuisDetailComponent } from '../../../../../../main/webapp/app/entities/guis/guis-detail.component';
import { GuisService } from '../../../../../../main/webapp/app/entities/guis/guis.service';
import { Guis } from '../../../../../../main/webapp/app/entities/guis/guis.model';

describe('Component Tests', () => {

    describe('Guis Management Detail Component', () => {
        let comp: GuisDetailComponent;
        let fixture: ComponentFixture<GuisDetailComponent>;
        let service: GuisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [GuisDetailComponent],
                providers: [
                    GuisService
                ]
            })
            .overrideTemplate(GuisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GuisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GuisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Guis(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.guis).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
