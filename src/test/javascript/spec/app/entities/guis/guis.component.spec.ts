/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { GuisComponent } from '../../../../../../main/webapp/app/entities/guis/guis.component';
import { GuisService } from '../../../../../../main/webapp/app/entities/guis/guis.service';
import { Guis } from '../../../../../../main/webapp/app/entities/guis/guis.model';

describe('Component Tests', () => {

    describe('Guis Management Component', () => {
        let comp: GuisComponent;
        let fixture: ComponentFixture<GuisComponent>;
        let service: GuisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [GuisComponent],
                providers: [
                    GuisService
                ]
            })
            .overrideTemplate(GuisComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GuisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GuisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Guis(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.guis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
