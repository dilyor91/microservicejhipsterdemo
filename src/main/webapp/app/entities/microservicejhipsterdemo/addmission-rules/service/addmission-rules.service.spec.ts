import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAddmissionRules } from '../addmission-rules.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../addmission-rules.test-samples';

import { AddmissionRulesService } from './addmission-rules.service';

const requireRestSample: IAddmissionRules = {
  ...sampleWithRequiredData,
};

describe('AddmissionRules Service', () => {
  let service: AddmissionRulesService;
  let httpMock: HttpTestingController;
  let expectedResult: IAddmissionRules | IAddmissionRules[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AddmissionRulesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AddmissionRules', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const addmissionRules = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(addmissionRules).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AddmissionRules', () => {
      const addmissionRules = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(addmissionRules).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AddmissionRules', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AddmissionRules', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AddmissionRules', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAddmissionRulesToCollectionIfMissing', () => {
      it('should add a AddmissionRules to an empty array', () => {
        const addmissionRules: IAddmissionRules = sampleWithRequiredData;
        expectedResult = service.addAddmissionRulesToCollectionIfMissing([], addmissionRules);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(addmissionRules);
      });

      it('should not add a AddmissionRules to an array that contains it', () => {
        const addmissionRules: IAddmissionRules = sampleWithRequiredData;
        const addmissionRulesCollection: IAddmissionRules[] = [
          {
            ...addmissionRules,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAddmissionRulesToCollectionIfMissing(addmissionRulesCollection, addmissionRules);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AddmissionRules to an array that doesn't contain it", () => {
        const addmissionRules: IAddmissionRules = sampleWithRequiredData;
        const addmissionRulesCollection: IAddmissionRules[] = [sampleWithPartialData];
        expectedResult = service.addAddmissionRulesToCollectionIfMissing(addmissionRulesCollection, addmissionRules);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addmissionRules);
      });

      it('should add only unique AddmissionRules to an array', () => {
        const addmissionRulesArray: IAddmissionRules[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const addmissionRulesCollection: IAddmissionRules[] = [sampleWithRequiredData];
        expectedResult = service.addAddmissionRulesToCollectionIfMissing(addmissionRulesCollection, ...addmissionRulesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const addmissionRules: IAddmissionRules = sampleWithRequiredData;
        const addmissionRules2: IAddmissionRules = sampleWithPartialData;
        expectedResult = service.addAddmissionRulesToCollectionIfMissing([], addmissionRules, addmissionRules2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addmissionRules);
        expect(expectedResult).toContain(addmissionRules2);
      });

      it('should accept null and undefined values', () => {
        const addmissionRules: IAddmissionRules = sampleWithRequiredData;
        expectedResult = service.addAddmissionRulesToCollectionIfMissing([], null, addmissionRules, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(addmissionRules);
      });

      it('should return initial array if no AddmissionRules is added', () => {
        const addmissionRulesCollection: IAddmissionRules[] = [sampleWithRequiredData];
        expectedResult = service.addAddmissionRulesToCollectionIfMissing(addmissionRulesCollection, undefined, null);
        expect(expectedResult).toEqual(addmissionRulesCollection);
      });
    });

    describe('compareAddmissionRules', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAddmissionRules(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAddmissionRules(entity1, entity2);
        const compareResult2 = service.compareAddmissionRules(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAddmissionRules(entity1, entity2);
        const compareResult2 = service.compareAddmissionRules(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAddmissionRules(entity1, entity2);
        const compareResult2 = service.compareAddmissionRules(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
