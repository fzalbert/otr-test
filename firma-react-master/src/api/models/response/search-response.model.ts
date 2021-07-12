import { ServantsResponse } from './servants-response.model';
import { SubCategoriesResponse } from './sub-categories-response.model';
import { CategoriesResponse } from './categories-response.model';
export interface SearchResponse {
    categories: CategoriesResponse[];
    searchType: number;
    subCategories: SubCategoriesResponse[];
    servants: ServantsResponse[];
}