package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasă abstractă care tipizează parametrii comuni la toate documentele.
 * Aleg să lucrez cu obiecte după clase specifice documentelor ca să evit
 * sintaxa greoaie de Jackson pe cât posibil în implementare. Ar trebui
 * să facă și verificările mai ușoare.
 */
public abstract class Document<T extends Document.Point> {
    protected String biddingZoneDomain;
    protected String periodStart;
    protected String periodEnd;
    protected String resolution;
    protected List<T> points;

    public Document(ObjectNode node) {
        this.biddingZoneDomain = node.get("biddingZoneDomain").asText();
        this.periodStart = node.get("periodStart").asText();
        this.periodEnd = node.get("periodEnd").asText();
        this.resolution = node.get("resolution").asText();
    }

    protected void loadPoints(ObjectNode node) {
        points = new ArrayList<>();
        ArrayNode pointsNode = (ArrayNode) node.get("points");

        for (JsonNode pointNode : pointsNode) {
            points.add(createPoint((ObjectNode) pointNode));
        }
    }

    protected abstract T createPoint(ObjectNode node);

    /**
     * Clasă abstractă care tipizează intervalIndex, comun tuturor Points.
     */
    public static abstract class Point {
        int intervalIndex;

        public Point(ObjectNode node) {
            this.intervalIndex = node.get("intervalIndex").asInt();
        }
    }


    /**
     * Day Ahead Market Prices
     */
    public static class DamPrices extends Document<DamPrices.DamPricesPoint> {
        String currencyUnit;
        String priceMeasureUnit;

        public DamPrices(ObjectNode node) {
            super(node);
            this.currencyUnit = node.get("currencyUnit").asText();
            this.priceMeasureUnit = node.get("priceMeasureUnit").asText();
            loadPoints(node);
        }

        @Override
        protected DamPricesPoint createPoint(ObjectNode node) {
            return new DamPricesPoint(node);
        }

        public static class DamPricesPoint extends Point {
            Double priceAmount;

            public DamPricesPoint(ObjectNode node) {
                super(node);
                this.priceAmount = node.get("priceAmount").asDouble();
            }
        }
    }

    /**
     * Day Ahead Market Price Predictions
     */
    public static class DamPricesPrediction extends Document<DamPricesPrediction.DamPricesPredictionPoint> {
        String currencyUnit;
        String priceMeasureUnit;

        public DamPricesPrediction(ObjectNode node) {
            super(node);
            this.currencyUnit = node.get("currencyUnit").asText();
            this.priceMeasureUnit = node.get("priceMeasureUnit").asText();
            loadPoints(node);
        }

        @Override
        protected DamPricesPredictionPoint createPoint(ObjectNode node) {
            return new DamPricesPredictionPoint(node);
        }

        public static class DamPricesPredictionPoint extends Point {
            Double predictedPriceEurMwh;
            Double spikeProbability;

            public DamPricesPredictionPoint(ObjectNode node) {
                super(node);
                this.predictedPriceEurMwh = node.get("predictedPriceEurMwh").asDouble();
                this.spikeProbability = node.get("spikeProbability").asDouble();
            }
        }
    }

    /**
     * Grid Load
     */
    public static class GridLoad extends Document<GridLoad.GridLoadPoint> {
        String quantityMeasureUnit;

        public GridLoad(ObjectNode node) {
            super(node);
            this.quantityMeasureUnit = node.get("quantityMeasureUnit").asText();
            loadPoints(node);
        }

        @Override
        protected GridLoadPoint createPoint(ObjectNode node) {
            return new GridLoadPoint(node);
        }

        public static class GridLoadPoint extends Point {
            Double quantity;
            Double reservesRequiredMW;

            public GridLoadPoint(ObjectNode node) {
                super(node);
                this.quantity = node.get("quantity").asDouble();
                this.reservesRequiredMW = node.get("reservesRequiredMW").asDouble();
            }
        }
    }

    /**
     * Grid Load Predictions
     */
    public static class GridLoadPrediction extends Document<GridLoadPrediction.GridLoadPredictionPoint> {
        String quantityMeasureUnit;

        public GridLoadPrediction(ObjectNode node) {
            super(node);
            this.quantityMeasureUnit = node.get("quantityMeasureUnit").asText();
            loadPoints(node);
        }

        @Override
        protected GridLoadPredictionPoint createPoint(ObjectNode node) {
            return new GridLoadPredictionPoint(node);
        }

        public static class GridLoadPredictionPoint extends Point {
            Double quantity;
            Double reservesRequiredMW;

            public GridLoadPredictionPoint(ObjectNode node) {
                super(node);
                this.quantity = node.get("quantity").asDouble();
                this.reservesRequiredMW = node.get("reservesRequiredMW").asDouble();
            }
        }
    }

    /**
     * Imbalance Prices
     */
    public static class ImbalancePrices extends Document<ImbalancePrices.ImbalancePricesPoint> {
        String currencyUnit;
        String priceMeasureUnit;

        public ImbalancePrices(ObjectNode node) {
            super(node);
            this.currencyUnit = node.get("currencyUnit").asText();
            this.priceMeasureUnit = node.get("priceMeasureUnit").asText();
            loadPoints(node);
        }

        @Override
        protected ImbalancePricesPoint createPoint(ObjectNode node) {
            return new ImbalancePricesPoint(node);
        }

        public static class ImbalancePricesPoint extends Point {
            Double positiveImbalancePrice;
            Double negativeImbalancePrice;

            public ImbalancePricesPoint(ObjectNode node) {
                super(node);
                this.positiveImbalancePrice = node.get("positiveImbalancePrice").asDouble();
                this.negativeImbalancePrice = node.get("negativeImbalancePrice").asDouble();
            }
        }
    }

    /**
     * Imbalance Volumes
     */
    public static class ImbalanceVolumes extends Document<ImbalanceVolumes.ImbalanceVolumesPoint> {
        String quantityMeasureUnit;

        public ImbalanceVolumes(ObjectNode node) {
            super(node);
            this.quantityMeasureUnit = node.get("quantityMeasureUnit").asText();
            loadPoints(node);
        }

        @Override
        protected ImbalanceVolumesPoint createPoint(ObjectNode node) {
            return new ImbalanceVolumesPoint(node);
        }

        public static class ImbalanceVolumesPoint extends Point {
            Double positiveImbalanceVol;
            Double negativeImbalanceVol;

            public ImbalanceVolumesPoint(ObjectNode node) {
                super(node);
                this.positiveImbalanceVol = node.get("positiveImbalanceVol").asDouble();
                this.negativeImbalanceVol = node.get("negativeImbalanceVol").asDouble();
            }
        }
    }

}
