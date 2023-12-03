package com.assignments.ecomerce.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "CartDetail")
@IdClass(CartDetail.CartDetailId.class)
public class CartDetail {
    @Id
    @Column(name = "userId")
    private Integer userId;

    @Id
    @Column(name = "productId")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unitPrice")
    private Double unitPrice;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CartDetail() {

    }

    public static class CartDetailId implements Serializable {
        private Integer userId;
        private Integer productId;

        public CartDetailId() {
            // Constructor mặc định
        }

        public CartDetailId(Integer userId, Integer productId) {
            this.userId = userId;
            this.productId = productId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((userId == null) ? 0 : userId.hashCode());
            result = prime * result + ((productId == null) ? 0 : productId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CartDetailId other = (CartDetailId) obj;
            if (userId == null) {
                if (other.userId != null)
                    return false;
            } else if (!userId.equals(other.userId))
                return false;
            if (productId == null) {
                if (other.productId != null)
                    return false;
            } else if (!productId.equals(other.productId))
                return false;
            return true;
        }
    }
}