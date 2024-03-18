import { Button, Container, Stack } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Bounce, toast } from "react-toastify";
import { ServiceApi } from "../api";
import { LayoutEvent } from "../layout";
import ModalCancel from "../components/ModalCancel";

const Order = ({ booking, packageDetail }) => {
  console.log("🚀 ~ Order ~ booking:", booking);
  const [order, setorder] = useState(null);
  const [openModalCanCel, setOpenModalCancel] = useState(false);
  const [bookingId, setBookingId] = useState(null);
  const [reload, setReload] = useState(1);
  const getOrrderDetail = async () => {
    const token = sessionStorage.getItem("token")
      ? JSON.parse(sessionStorage.getItem("token"))
      : "";
    try {
      const res = await ServiceApi.getOrderDetail(token);
            setorder(res);
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
    getOrrderDetail();
  }, []);

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
  const handleCancelBooking = async (id) => {
    try {
      const token = sessionStorage.getItem("token")
        ? JSON.parse(sessionStorage.getItem("token"))
        : "";
      await ServiceApi.cancelBooking(id, token);
      toast.success("🦄 cancel success!", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
      getOrrderDetail();
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

  return (
    <LayoutEvent showBtn={true}>
      <Container maxWidth="lg">
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
                  <th
                    style={{
                      minWidth: "3rem",
                    }}
                  >
                    No
                  </th>
                  <th
                    style={{
                      minWidth: "10rem",
                      width: 'auto',
                    }}
                  >
                    Date
                  </th>
                  <th
                    style={{
                      minWidth: "15rem",
                    }}
                  >
                    Status booking
                  </th>
                  <th
                    style={{
                      minWidth: "15rem",
                    }}
                  >
                    Status payment
                  </th>
                  <th
                  style={{
                    minWidth: "5rem",
                  }}>Slot</th>
                  <th style={{
                    minWidth: "10rem",
                  }}>Total Cost</th>
                  <th
                    style={{
                      minWidth: "10rem",
                    }}
                  >
                    Edit Status
                  </th>
                  <th
                  style={{
                    minWidth: "15rem",
                  }}>
                    Refunded money
                  </th>
                </tr>
              </thead>
              <tbody>
                {order?.map((item, index) => {
                  return (
                    <tr key={item?.bookingId}>
                      <td
                        style={{
                          textAlign: "center",
                        }}
                      >
                        {index + 1}
                      </td>
                      <td
                        style={{
                          textAlign: "center",
                        }}
                      >
                        <Stack
                          direction={"row"}
                          spacing={3}
                          alignItems={"center"}
                          justifyContent={"center"}
                        >
                          {/* /.col */}
                          <div className="col-lg-10 col-xs-9 col-550-12">
                            <p style={{ margin: '0rem', paddingBottom: '1rem' }} className="margin-0 lineheight18">
                              <span className="family-avi-medium darkblue">
                                <b>
                                  <br /> {formatTime(item?.startTime)}-{" "}
                                  {formatTime(item?.endTime)} (MST)
                                </b>
                              </span>
                            </p>
                          </div>
                          {/* /.col */}
                        </Stack>
                        {/* /.row */}
                      </td>
                      <td
                        style={{
                          textAlign: "center",
                        }}
                      >
                        {item?.bookingStatus}
                      </td>
                      <td
                        style={{
                          textAlign: "center",
                        }}
                      >
                        {item?.paymentStatus}
                      </td>
                      <td style={{ textAlign: 'center' }}>{item?.partySize}</td>
                      <td style={{ textAlign: 'center' }}>${item?.totalCost?.toLocaleString()}</td>
                      <td
                        style={{
                          display: "flex",
                          alignItems: "center",
                          justifyContent: "center",
                        }}
                      >
                        {item?.bookingStatus === "PENDING" && (
                          <Button
                            variant="contained"
                            sx={{ background: "red", marginTop: "0.5rem" }}
                            onClick={() => {
                              setBookingId(item?.bookingId);
                              setOpenModalCancel(true);
                            }}
                          >
                            Cancel
                          </Button>
                        )}
                      </td>
                      <td style={{ textAlign: 'center' }}>
                        {(item?.bookingStatus === 'CANCELLED' && Number(item?.refunded) > 0) || item?.bookingStatus === 'REFUNDED'
                          ? '$'+item?.refunded?.toLocaleString() : ''}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </Container>
      {openModalCanCel && (
        <ModalCancel
          open={openModalCanCel}
          handleClose={() => setOpenModalCancel(false)}
          handleConfirm={() => {
            handleCancelBooking(bookingId);
            setOpenModalCancel(false);
          }}
        />
      )}
    </LayoutEvent>
  );
};

export default Order;
