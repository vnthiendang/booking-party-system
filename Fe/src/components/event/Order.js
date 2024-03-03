import { Stack } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Bounce, toast } from "react-toastify";
import { ServiceApi } from "../../api";

const Order = ({ booking, packageDetail }) => {
  console.log("🚀 ~ Order ~ booking:", booking);
  const [order, setorder] = useState(null);

  const getOrrderDetail = async (token) => {
    try {
      const res = await ServiceApi.getOrderDetail(token);
      setorder(
        res?.filter((item) => item?.bookingId === booking?.bookingId)?.[0]
      );
    } catch (error) {
      toast.error("🦄 Something went wrong!", {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
    }
  };
  useEffect(() => {
    const token = sessionStorage.getItem("token")
      ? JSON.parse(sessionStorage.getItem("token"))
      : "";
    getOrrderDetail(token);
  }, [booking]);

  console.log("🚀 ~ Order ~ order:", order);
  //startTime
  //endTime
  //totalCost
  function formatTime(timeString) {
    const dateTime = new Date(timeString);
    const hours = dateTime.getHours().toString().padStart(2, "0");
    const minutes = dateTime.getMinutes().toString().padStart(2, "0");
    return `${hours}:${minutes}`;
  }

  return (
    <div>
      <div
        style={{
          margin: "20px 0",
        }}
      >
        <table
          className="table table-striped table-hover"
          style={{
            borderBottom: "2px solid #e7e7e7",
          }}
        >
          <thead
            style={{
              backgroundColor: "#e7e7e7",
              height: "36px",
            }}
          >
            <tr>
              <th>Item</th>
              <th
                style={{
                  width: "20%",
                }}
              >
                Quantity
              </th>
              <th
                style={{
                  width: "20%",
                }}
              >
                Price
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <Stack direction={"row"} spacing={3} alignItems={"center"}>
                  <div className="col-lg-2 col-xs-3 hidden-550">
                    <img
                      src="https://thebigboxstorage.blob.core.windows.net/thebigbox/images/assignment/f6ee011d8f134066a4d5cad369c249e2.jpg"
                      alt="Venue Image"
                      width={49}
                      height={49}
                    />
                  </div>
                  {/* /.col */}
                  <div className="col-lg-10 col-xs-9 col-550-12">
                    <p className="bottommargin-0 lineheight18">
                      <span className="family-avi-medium darkblue">
                        Package: <b>{packageDetail?.packageName}</b>
                        <br />
                        Location: <b>{packageDetail?.venue}</b>
                        <br />
                        <b>
                          <br /> {formatTime(order?.startTime)}-{" "}
                          {formatTime(order?.endTime)} (MST)
                        </b>
                      </span>
                    </p>
                    <p className="bottommargin-0 lineheight16">
                      <span className="font12 darkblue">
                        1 # of kids (Max. Room Seating = 16) , 1 # of adults
                        (Max. Room Seating = 16)
                      </span>
                      <span className="font12 darkblue">
                        Total Capacity: {packageDetail?.capacity}
                      </span>
                    </p>
                  </div>
                  {/* /.col */}
                </Stack>
                {/* /.row */}
              </td>
              <td>1</td>
              <td>${packageDetail?.price?.toLocaleString()}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <p
        style={{
          padding: "10px 0",
          borderBottom: "2px solid #e7e7e7",
          textAlign: "end",
        }}
      >
        Total discount : $00.00
      </p>
      <p
        style={{
          padding: "10px 0",
          borderBottom: "2px solid #e7e7e7",
          textAlign: "end",
          background: "#e7e7e7",
          paddingRight: "10px",
        }}
      >
        Grand Total: ${order?.totalCost?.toLocaleString()}
      </p>
    </div>
  );
};

export default Order;
